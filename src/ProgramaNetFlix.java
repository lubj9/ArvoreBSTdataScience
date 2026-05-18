public class ProgramaNetFlix {
    private String id;
    private String title;
    private String type;
    private String description;
    private int releaseYear;
    private String ageCertification;
    private int runtime;
    private String genres;
    private String productionCountries;
    private int seasons;
    private String imdbId;
    private double imdbScore;
    private int imdbVotes;
    private double tmdbPopularity;
    private double tmdbScore;

    public ProgramaNetFlix(String id, String title, String type, String description, int releaseYear,
                           String ageCertification, int runtime, String genres, String productionCountries,
                           int seasons, String imdbId, double imdbScore, int imdbVotes,
                           double tmdbPopularity, double tmdbScore) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.releaseYear = releaseYear;
        this.ageCertification = ageCertification;
        this.runtime = runtime;
        this.genres = genres;
        this.productionCountries = productionCountries;
        this.seasons = seasons;
        this.imdbId = imdbId;
        this.imdbScore = imdbScore;
        this.imdbVotes = imdbVotes;
        this.tmdbPopularity = tmdbPopularity;
        this.tmdbScore = tmdbScore;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getAgeCertification() {
        return ageCertification;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getProductionCountries() {
        return productionCountries;
    }

    public int getSeasons() {
        return seasons;
    }

    public String getImdbId() {
        return imdbId;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public int getImdbVotes() {
        return imdbVotes;
    }

    public double getTmdbPopularity() {
        return tmdbPopularity;
    }

    public double getTmdbScore() {
        return tmdbScore;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setAgeCertification(String ageCertification) {
        this.ageCertification = ageCertification;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setProductionCountries(String productionCountries) {
        this.productionCountries = productionCountries;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    public void setImdbVotes(int imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public void setTmdbPopularity(double tmdbPopularity) {
        this.tmdbPopularity = tmdbPopularity;
    }

    public void setTmdbScore(double tmdbScore) {
        this.tmdbScore = tmdbScore;
    }

    public String toCSV() {
        return id + "," +
                formatar(title) + "," +
                type + "," +
                formatar(description) + "," +
                releaseYear + "," +
                ageCertification + "," +
                runtime + "," +
                formatar(genres) + "," +
                formatar(productionCountries) + "," +
                seasons + "," +
                imdbId + "," +
                imdbScore + "," +
                imdbVotes + "," +
                tmdbPopularity + "," +
                tmdbScore;
    }

    private String formatar(String texto) {
        if (texto == null) {
            return "";
        }

        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nTítulo: " + title +
                "\nTipo: " + type +
                "\nDescrição: " + description +
                "\nAno: " + releaseYear +
                "\nClassificação: " + ageCertification +
                "\nDuração: " + runtime +
                "\nGêneros: " + genres +
                "\nPaíses: " + productionCountries +
                "\nTemporadas: " + seasons +
                "\nIMDB ID: " + imdbId +
                "\nIMDB Score: " + imdbScore +
                "\nIMDB Votos: " + imdbVotes +
                "\nTMDB Popularidade: " + tmdbPopularity +
                "\nTMDB Score: " + tmdbScore;
    }
}