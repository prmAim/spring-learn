package ru.geekbrain.lesson04.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Разделение уровней сервиса [валидация front] и уровня сущности DB из-за принципа единства отвественности
 */
public class ProductDto {

    private Long id;

    // @NotBlank - валидация на сервере. Значение не пустая, без пробелов, без табуляции
    // @NotEmpty - валидация на сервере. Значение не пустая, но пропустить пробелы, табуляция ...
    // @NotNull  - валидация на сервере. Значение не Null.
    // @Email    - валидация на сервере. Значение на эл. почту.
    // @Pattern  - Валидация. регулярные выражения: одни буквы не менее 2 шт. Без точек
    // message   - сообщение при ошибке валидации
    @Pattern(regexp = "^(?=.*?[A-Z]).{2,}$", message = "Name product is unknown")
    @NotBlank
    private String name;

    @Max(value = 100000, message = "Max cost is 100 000")
    @Min(value = 0, message = "Min cost is 0")
    @NotBlank
    private BigDecimal cost;

//    @Pattern(regexp = "^(?=.*?[0-9])(?=.*?[A-Z]).{8,}$", message = "Password too simple")
//    @NotBlank
//    private String password;
//    сверка 
//    private String matchingPassword;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BigDecimal cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
