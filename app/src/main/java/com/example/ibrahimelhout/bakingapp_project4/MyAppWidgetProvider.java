package com.example.ibrahimelhout.bakingapp_project4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;
import com.orhanobut.hawk.Hawk;


public class MyAppWidgetProvider extends AppWidgetProvider {


    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String recipeName, String ingredients) {


        Intent intent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.recipeNameWiget, recipeName);
        views.setTextViewText(R.id.ingredientTvWidget, ingredients);
        views.setOnClickPendingIntent(R.id.buttonOpenRecipies, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (Hawk.contains(Constants.RECIPE_SAVED_FOR_WIDGET)) {
            Recipe recipe = Hawk.get(Constants.RECIPE_SAVED_FOR_WIDGET);


            String ingredient = "";
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                ingredient = recipe.getIngredients().get(i).getIngredient() + ". \n" + ingredient;
            }


            for (int appWId : appWidgetIds) {
                updateWidget(context, appWidgetManager, appWId, recipe.getName(), ingredient);

            }


        } else {



            for (int appWId : appWidgetIds) {
                updateWidget(context, appWidgetManager, appWId, context.getString(R.string.pick_recipe), "");

            }


        }


//        for (int appWId : appWidgetIds){
//
//            Intent intent = new Intent(context, RecipesActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
//
//            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
//            views.setOnClickPendingIntent(R.id.buttonOpenRecipies,pendingIntent );
//            appWidgetManager.updateAppWidget(appWId,views);
//
//        }
    }

}
