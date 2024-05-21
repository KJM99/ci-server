package com.example.devopsboard.service;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.devopsboard.domain.dto.request.BoardRequest;
import com.example.devopsboard.domain.dto.response.BoardResponse;
import com.example.devopsboard.domain.entity.Board;
import com.example.devopsboard.domain.repository.BoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private BoardServiceImpl boardService;

    private List<Board> mockBoards;

    @BeforeEach
    void setUp() {
        Board b1 = new Board(1L, "test1", "test1");
        Board b2 = new Board(2L, "test2", "test2");
        mockBoards = Stream.of(b1, b2).collect(Collectors.toList());
    }

    @Test
    void getBoard() {
        // given`
        when(boardRepository.findAll()).thenReturn(mockBoards);
        List<BoardResponse> boardResponses= boardService.getBoard();
        // assertThat(boardResponses).hasSize(2);
        // assertThat(boardResponses.get(0).getName()).isEqualTo("Name1");
        // assertThat(boardResponses.get(0).getText()).isEqualTo("Text1");
        // assertThat(boardResponses.get(1).getName()).isEqualTo("Name2");
        // assertThat(boardResponses.get(1).getText()).isEqualTo("Text2");
        // when

        // then
    }

    @Test
    void addBoard() {
        Board board = new Board(1L, "test", "test");

        boardRepository.save(board);

        Optional<Board> byId = boardRepository.findById(1L);
        Board board1 = byId.orElse(new Board());

        assertEquals("test",board1.getText());


    }
}

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest2 {
    @Mock private BoardRepository boardRepository;
    @InjectMocks private BoardServiceImpl boardService;

    @Test
    void getById() {
        Board board = new Board(1L, "test", "test");
        BDDMockito.given(boardRepository.findById(1L))
            .willReturn(Optional.of(board));


        BoardResponse byId = boardService.getById(1L);

//        행위 검증
        Mockito.verify(boardRepository, Mockito.times(1)).findById(1L);
//        상태 검증
        assertEquals("test", byId.name());
        assertEquals("test", byId.text());
        // assertNotNull(byId.id());
    }
    @Test
    void getByIdNotExist() {
        BDDMockito.given(boardRepository.findById(1L)).willReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, ()->{
            boardService.getById(1L);
        });
        Mockito.verify(boardRepository,Mockito.times(1)).findById(1L);

    }

    @Test
    void getAll() {

        BDDMockito.given(boardRepository.findAll()).willReturn(
            List.of(new Board(1L,"test", "test"),new Board(2L,"test", "test")));

        List<BoardResponse> all = boardService.getBoard();

        assertEquals(2, all.size());
        assertEquals("test", all.get(1).name());
        Mockito.verify(boardRepository).findAll();
    }

    @Test
    void saveBoard() {
        BoardRequest request = new BoardRequest("test", "test");
        Board entity = request.toEntity();
        BDDMockito.given(boardRepository.save(entity))
            .willReturn(entity);

        boardService.addBoard(request);

        Mockito.verify(boardRepository, Mockito.times(1)).save(entity);
    }
}