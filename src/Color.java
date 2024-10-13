public enum Color {
    RED("\033[31m"),
    YELLOW("\033[33m"),
    GREEN("\033[32m"),
    BLUE("\033[34m"),
    PURPLE("\033[35m"),
    DEFAULT("\033[0m");
    private String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
