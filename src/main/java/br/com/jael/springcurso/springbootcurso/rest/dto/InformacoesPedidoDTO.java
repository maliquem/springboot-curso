package br.com.jael.springcurso.springbootcurso.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {
    private Integer codigo;
    private BigDecimal cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String data;
    private String status;
    private List<InformacaoItemPedidoDTO> itens;
}
