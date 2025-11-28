package database.request.utils;

public class FunctionToQuery {

    private final StringBuilder query;
    private final String queryParam;

    public FunctionToQuery(StringBuilder query, String queryParam){
        this.query = query;
        this.queryParam = queryParam;

        if(query.indexOf(queryParam) == -1)
            throw new IllegalArgumentException("Параметра нет в запросе");
    }

    private String createNewQueryParam(String functionName, String[] params){
        StringBuilder newParam = new StringBuilder();
        newParam.append(functionName).append('(');

        if(params != null)
            for(String par : params){
                newParam.append(par).append(",");
            }

        newParam.append(queryParam).append(')');

        return newParam.toString();
    }

    public FunctionToQuery addFunction(String functionName){
        String newParam = createNewQueryParam(functionName, null);
        int indexOfParam = query.indexOf(queryParam);

        return new FunctionToQuery(query.replace(indexOfParam, indexOfParam + queryParam.length(), newParam.toString()), newParam.toString());
    }

    public FunctionToQuery addFunction(String functionName, String... params){
        String newParam = createNewQueryParam(functionName, params);
        int indexOfParam = query.indexOf(queryParam);

        return new FunctionToQuery(query.replace(indexOfParam, indexOfParam + queryParam.length(), newParam.toString()), newParam.toString());
    }

    public String build(){
        return this.query.toString();
    }

    public static FunctionToQuery from(StringBuilder query, String param) {
        return new FunctionToQuery(query, param);
    }
}
