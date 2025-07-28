package br.com.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {

    private String title;

    @JsonAlias("download_count")
    private Integer downloadCount;
    private List<String> languages;
    private List<AuthorDTO> authors;

    public String getTitle() {
        return title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", downloadCount=" + downloadCount +
                ", languages=" + languages +
                ", authors=" + authors +
                '}';
    }
}
