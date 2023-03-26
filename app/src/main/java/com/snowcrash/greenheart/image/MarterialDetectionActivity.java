package com.snowcrash.greenheart.image;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;
import com.snowcrash.greenheart.model.GarbageCollectionMap;
import com.snowcrash.greenheart.R;
import com.snowcrash.greenheart.model.CollectionState;
import com.snowcrash.greenheart.helpers.ImageHelperActivity;
import com.snowcrash.greenheart.ml.GreenHeart;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class MarterialDetectionActivity extends ImageHelperActivity {
    private ImageLabeler imageLabeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalModel localModel = new LocalModel.Builder().setAssetFilePath("green_heart_gallery.tflite").build();
        CustomImageLabelerOptions options = new CustomImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.7f)
                .setMaxResultCount(5)
                .build();
        imageLabeler = ImageLabeling.getClient(options);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void runDetection(Bitmap bitmap, int requestCode) {
        if(requestCode == 1064){
            InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
            bitmap = inputImage.getBitmapInternal();
        }
        classifyImage(bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void classifyImage(Bitmap bitmap){
        try {

            GreenHeart model = GreenHeart.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorImage image = TensorImage.fromBitmap(bitmap);

            // Runs model inference and gets result.
            GreenHeart.Outputs outputs = model.process(image);
            List<Category> probability = outputs.getProbabilityAsCategoryList();

            Optional<Category> categoryOp = probability.stream().
                    filter(category -> category.getScore() > 0.5).findFirst();




            if(categoryOp.isPresent()){
                getOutputTextView().setText("Material is :"+categoryOp.get().getLabel()+". Please select your state to find garbage collection point.");
                findAndShowGarbageCollectionPoint(categoryOp.get().getLabel());
            } else {
                getOutputTextView().setText("Could not classify!!");
            }

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    public void findAndShowGarbageCollectionPoint(String material) throws IOException {
        LinkedHashMap<String, String> statesMap = new LinkedHashMap<>();
        statesMap.put("select", "Select State");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        try{
            InputStream inputStream = getResources().openRawResource(R.raw.states);
            InputStream garbageCollectionMapStream = getResources().openRawResource(R.raw.garbagecollectionpointmap);
            List<CollectionState> dataList = mapper.readValue(inputStream, new TypeReference<List<CollectionState>>(){});
            List<GarbageCollectionMap> garbageCollectionMapList = mapper.readValue(garbageCollectionMapStream, new TypeReference<List<GarbageCollectionMap>>(){});
            for (CollectionState data : dataList) {
                statesMap.put(data.getCode(), data.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<>(statesMap.values()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spinner = (Spinner) findViewById(R.id.my_spinner);

            spinner.setAdapter(adapter);
            spinner.setVisibility(View.VISIBLE);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Handle the user's selection here
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    String selectedKey = getKeyFromValue(statesMap, selectedItem);

                    if(selectedKey!="select"){
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            List<GarbageCollectionMap> garbageCollectionPoints = garbageCollectionMapList.stream().filter(x -> x.getState().contentEquals(selectedKey) && x.getMaterials().contains(material.toLowerCase())).collect(Collectors.toList());
                            System.out.println(garbageCollectionPoints);
                            getOutputTextView().setText("Material is :"+material+ ". Please see below garbage collection points:\n");
                            garbageCollectionPoints.forEach(x->{
                                String existingText = getOutputTextView().getText().toString();
                                String newText = existingText + "\n" + x.getFullAddress() + "\n";
                                getOutputTextView().setText(newText);
                            });
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }

    private String getKeyFromValue(LinkedHashMap<String, String> map, String value) {
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

}
