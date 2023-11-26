package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {
    @InjectMocks
    private ValidacaoPetDisponivel validacao;
    @Mock
    private PetRepository petRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;
    @Mock
    private Pet pet;

    @Test
    public void deveValidarPetDisponivel() {
        //ARRANGE
        BDDMockito.given(petRepository.getReferenceById(Mockito.anyLong())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(false);
        //ACT + ASSERT

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    public void naodeveValidarPetDisponivel() {
        //ARRANGE

        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(true);
        //ACT + ASSERT

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}