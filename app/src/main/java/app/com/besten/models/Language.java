package app.com.besten.models;

public class Language implements Comparable<Language> {
    private String name;
    private Integer rank;
    private String content;
    private String icon;

    Language() { }

    public String getName() {
        return name;
    }

    public Integer getRank() {
        return rank;
    }

    public String getContent() {
        return content;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public int compareTo(Language language) {
        if (getRank() == null || language.getRank() == null) {
            return 0;
        }
        return getRank().compareTo(language.getRank());
    }
}
