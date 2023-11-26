package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraProbabilidadeAdocaoTest {


    @Test
    @DisplayName("Deve retornar uma probabilidade alta de adoção do pet")
    public void deveRetornarUmaProbabilidadeAltaDeAdocao() {
        //arrange
        var abrigo = new Abrigo(new CadastroAbrigoDto("abrigo do sul", "21 99604-1143", "abrigosul@abrigosul.com.br"));
        var pet = new Pet(new CadastroPetDto(TipoPet.GATO, "ana katarina", "vira lata", 4, "cinza-preto", 4.0f), abrigo);

        //act
        var calculadora = new CalculadoraProbabilidadeAdocao();
        var probabilidade = calculadora.calcular(pet);


        //assert
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
    }

    @Test
    @DisplayName("Deve retornar uma probabilidade media de adoção do pet")
    public void deveRetornarUmaProbabilidadeMediaDeAdocao() {
        //arrange
        var abrigo = new Abrigo(new CadastroAbrigoDto("abrigo do sul", "21 99604-1143", "abrigosul@abrigosul.com.br"));
        var pet = new Pet(new CadastroPetDto(TipoPet.GATO, "ana katarina", "vira lata", 15, "cinza-preto", 4.0f), abrigo);

        //act
        var calculadora = new CalculadoraProbabilidadeAdocao();
        var probabilidade = calculadora.calcular(pet);


        //assert
        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
    }

}