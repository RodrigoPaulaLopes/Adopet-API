package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {
    @InjectMocks
    private AdocaoService adocaoService;
    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacaoSolicitacaoAdocaos = new ArrayList<>();


    @Mock
    private ValidacaoSolicitacaoAdocao validacaoSolicitacaoAdocao1;
    @Mock
    private ValidacaoSolicitacaoAdocao validacaoSolicitacaoAdocao2;


    @Mock
    private EmailService emailService;

    @Mock
    private List<ValidacaoSolicitacaoAdocao> validacoes;


    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Mock
    private Pet pet;
    @Mock
    private Abrigo abrigo;
    @Mock
    private Tutor tutor;
    @Captor
    private ArgumentCaptor<Adocao> adocaoArgumentCaptor;

    @Test
    @DisplayName("Deve salvar um dado no banco de dados")
    public void deveSalvarDadoNoDB() {
        //arrange
        given(petRepository.getReferenceById(Mockito.anyLong())).willReturn(pet);
        given(tutorRepository.getReferenceById(Mockito.anyLong())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        this.solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        //act
        adocaoService.solicitar(solicitacaoAdocaoDto);

        //assert
        then(repository).should().save(adocaoArgumentCaptor.capture());
        Adocao adocaoSalva = adocaoArgumentCaptor.getValue();

        assertEquals(pet, adocaoSalva.getPet());
        assertEquals(tutor, adocaoSalva.getTutor());
        assertEquals(solicitacaoAdocaoDto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    @DisplayName("Deve verificar se os validadores são chamados")
    public void deveVerificarSeOsValidadoresSãoChamados() {
        //arrange
        given(petRepository.getReferenceById(Mockito.anyLong())).willReturn(pet);
        given(tutorRepository.getReferenceById(Mockito.anyLong())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        this.solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        //act
        adocaoService.solicitar(solicitacaoAdocaoDto);
        validacaoSolicitacaoAdocaos.addAll(Arrays.asList(validacaoSolicitacaoAdocao1, validacaoSolicitacaoAdocao2));
        //assert
        then(repository).should().save(adocaoArgumentCaptor.capture());
        Adocao adocaoSalva = adocaoArgumentCaptor.getValue();

        BDDMockito.then(validacaoSolicitacaoAdocao1).should().validar(this.solicitacaoAdocaoDto);
        BDDMockito.then(validacaoSolicitacaoAdocao2).should().validar(this.solicitacaoAdocaoDto);


    }
}
