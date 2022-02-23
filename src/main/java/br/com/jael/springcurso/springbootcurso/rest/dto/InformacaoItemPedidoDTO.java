package br.com.jael.springcurso.springbootcurso.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {
    private String nomeProduto;
    private Double precoProduto;
    private Integer quantidade;
}
