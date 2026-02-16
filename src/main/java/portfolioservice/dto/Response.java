package portfolioservice.dto;

public record Response<T>(int statusCode, T body) {
    public static Response<Void> status(int statusCode) {return new Response<>(statusCode, null);}
    public static <T> Response<T> ok( T body) {return new Response<>(200, body);}
}
