package com.ur.promobiproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ishanon 07-07-2018.
 */

public class Article {
    // Class to parse nested response.
    @SerializedName("status")
    private  String status;

    @SerializedName("copyright")
    private  String copyright;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @SerializedName("response")
    private Response response;

    public class Response{
        public ArrayList<Docs> getDocs() {
            return docs;
        }

        @SerializedName("docs")
        private ArrayList<Docs> docs;

    }

    public class Docs{

        @SerializedName("web_url")
        private String web_url;

        @SerializedName("_id")
        private String _id;

        @SerializedName("snippet")
        private String snippet;

        @SerializedName("source")
        private String source;

        public String getWeb_url() {
            return web_url;
        }

        public String getSnippet() {
            return snippet;
        }

        public String getSource() {
            return source;
        }

        public String getPub_date() {
            return pub_date;
        }

        public Headline getHeadline() {
            return headline;
        }

        @SerializedName("pub_date")
        private String pub_date;

        @SerializedName("headline")
        private Headline headline;

        public ArrayList<Multimedia> getMultimedia() {
            return multimedia;
        }

        @SerializedName("multimedia")
        private ArrayList<Multimedia> multimedia;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }

    public class Headline{

        public String getMain() {
            return main;
        }

        public String getPrint_headline() {
            return print_headline;
        }

        @SerializedName("main")
        private String main;

        @SerializedName("print_headline")
        private String print_headline;

    }

    public class Multimedia{

        @SerializedName("subtype")
        private String subtype;

        public String getSubtype() {
            return subtype;
        }

        public String getUrl() {

            return url;
        }

        @SerializedName("url")
        private String url;

    }
}
