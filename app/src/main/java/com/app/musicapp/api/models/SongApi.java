package com.app.musicapp.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongApi {
    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("image")
    public String image;
    @JsonProperty("path")
    public String path;
    @JsonProperty("artist_id")
    public int artist_id;
    @JsonProperty("artist")
    public Artist artist;

    public SongApi() {
    }

    public SongApi(int id, String name, String image, String path, int artist_id, Artist artist) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.path = path;
        this.artist_id = artist_id;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongApi song = (SongApi) o;
        return id == song.id &&
                artist_id == song.artist_id &&
                name.equals(song.name) &&
                image.equals(song.image) &&
                path.equals(song.path) &&
                artist.equals(song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, path, artist_id, artist);
    }

    @Override
    public String toString() {
        return "SongApi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", path='" + path + '\'' +
                ", artist_id=" + artist_id +
                ", artist=" + artist +
                '}';
    }
}
