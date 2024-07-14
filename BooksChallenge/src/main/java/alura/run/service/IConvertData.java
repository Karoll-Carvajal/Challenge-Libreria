package alura.run.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> clase);
}
