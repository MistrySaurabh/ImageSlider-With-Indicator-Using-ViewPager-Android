package com.app.autometicimageslideshow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Saurabh on 22-06-2016.
 */
public class ImageItemModel {
    @SerializedName("id")
    private int Id;

    @SerializedName("imageurl")
    private String ImageUrl;


    @SerializedName("category")
    private String Category;

    @SerializedName("title")
    private String Title;

    @SerializedName("description")
    private String Descreption;



    @SerializedName("pictures_result")
    public List<ImageItemModel> results;

    public ImageItemModel(int id,String imageUrl,String category,String title,String descreption) {
       Id=id;
       ImageUrl=imageUrl;
       Category=category;
       Title=title;
       Descreption=descreption;
    }

    public int getId() {
        return Id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getCategory() {
        return Category;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescreption() {
        return Descreption;
    }



    public List<ImageItemModel> getResults() {
        return results;
    }


}
