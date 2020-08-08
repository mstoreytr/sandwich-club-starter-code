package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView description = findViewById(R.id.description_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView knownAs = findViewById(R.id.also_known_tv);
        description.setText(sandwich.getDescription());
        origin.setText(sandwich.getPlaceOfOrigin());
        ingredients.setText(arrayToString(sandwich.getIngredients()));
        knownAs.setText(arrayToString(sandwich.getAlsoKnownAs()));

    }

    private String arrayToString(List<String> input) {
        StringBuilder output = new StringBuilder();
        if (input != null && !input.isEmpty()) {
            for (String value: input) {
                output.append(value).append(", ");
            }
            String result = output.toString();
            int index = result.lastIndexOf(", ");
            return result.substring(0, index);
        }
        return "";
    }
}
