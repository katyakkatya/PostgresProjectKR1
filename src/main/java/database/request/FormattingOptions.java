package database.request;

public record FormattingOptions(boolean inUpperCase, boolean inLowerCase, boolean isShortDisplay, boolean displayId,
                                boolean displayFullStatus) {
}
