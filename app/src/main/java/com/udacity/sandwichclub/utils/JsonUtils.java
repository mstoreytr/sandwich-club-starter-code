package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwich = new JSONObject(json);
            // create empty Sandwich
            Sandwich parsedSandwich = new Sandwich();
            // First get name info
            JSONObject nameObject = sandwich.optJSONObject("name");
            if (nameObject != null) {
                // Add main name
                parsedSandwich.setMainName(nameObject.optString("mainName"));
                parsedSandwich.setAlsoKnownAs(getStringArray(nameObject.optJSONArray("alsoKnownAs")));
            }
            // Add rest of details
            parsedSandwich.setImage(sandwich.optString("image"));
            parsedSandwich.setDescription(sandwich.optString("description"));
            parsedSandwich.setPlaceOfOrigin(sandwich.optString("placeOfOrigin"));
            parsedSandwich.setIngredients(getStringArray(sandwich.optJSONArray("ingredients")));
            return parsedSandwich;
        } catch (JSONException exception) {
            Log.e(JsonUtils.class.getName(), "JSON Parsing exception "+exception.getMessage());
            return null;
        }
    }

    /**
     * Creates a string list from JsonArray of Strings
     * @param jsonArray JSONArray of Strings
     * @return a list of strings
     */
    private static List<String> getStringArray(JSONArray jsonArray) {
        List<String> listOfStrings = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                listOfStrings.add(jsonArray.optString(i));
            }
        }
        return listOfStrings;
    }
}
