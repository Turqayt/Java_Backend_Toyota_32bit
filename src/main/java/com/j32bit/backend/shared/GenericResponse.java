package com.j32bit.backend.shared;

import com.j32bit.backend.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * This class is to be used when returning response from endpoint.
 * The class wraps the returning response with some other info fields.
 *
 *
 * <pre>
 *
 * {@code GenericResponse.createSuccessResponse(dto)}
 *
 * {@code GenericResponse.createSuccessResponse(dto)
 *                 .code("code")
 *                 .description("description")
 *                 .parameterList(Map.of("param","value")));}
 *
 * </pre>
 *
 *
 * @param type              ERROR,SUCCESS etc.
 * @param code              Short code of returning response {@link ResponseType}
 * @param description       Descriptive message about response
 * @param <T>               The data which is wrapped by {@link GenericResponse} class
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {

    private String type;
    private String code;
    private String description;
    private List<T> data;


    public GenericResponse(String type, List<T> data){
        this.type = type;
        this.data = data;
    }

    public GenericResponse(String type){
        this.type = type;
    }

    public static <T> GenericResponse<T> createErrorResponse(){
        return new GenericResponse<>(ResponseType.ERROR.toString());
    }

    public static <T> GenericResponse<T> createSuccessResponse(){
        return new GenericResponse<>(ResponseType.SUCCESS.toString());
    }

    public static <T> GenericResponse<T> createErrorResponse( T data){
        return new GenericResponse<>(ResponseType.ERROR.toString(), List.of(data));
    }

    public static <T> GenericResponse<T> createErrorResponse(List<T> data){
        return new GenericResponse<>(ResponseType.ERROR.toString(), data);
    }

    public static <T> GenericResponse<T> createSuccessResponse(T data){
        if (data == null) {
            return new GenericResponse<>(ResponseType.SUCCESS.toString());
        }
        return new GenericResponse<>(ResponseType.SUCCESS.toString(), List.of(data));
    }

    public static <T> GenericResponse<T> createSuccessResponse(List<T> data){
        return new GenericResponse<>(ResponseType.SUCCESS.toString(), data);
    }

    public GenericResponse<T> code(String code){
        this.code = code;
        return this;
    }

    public GenericResponse<T> description(String description){
        this.description = description;
        return this;
    }

}
