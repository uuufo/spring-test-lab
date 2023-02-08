package co.codingnomads.lab;

import co.codingnomads.lab.controller.MovieController;
import co.codingnomads.lab.entity.Movie;
import co.codingnomads.lab.repository.MovieRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringTestLab.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testGetAllMoviesSuccess() throws Exception {
        mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(MovieController.class))
                .andExpect(handler().method(MovieController.class.getMethod("getAllMovies")))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].rating").value(9.3))
                .andExpect(jsonPath("$[1].name").value("The Pursuit of Happyness"))
                .andExpect(jsonPath("$[1].rating").value(8.0));
    }

    @Test
    @Order(2)
    public void testGetAllMoviesSuccessUsingObjectMapper() throws Exception {
        byte[] jsonResponseBytes = mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(MovieController.class))
                .andExpect(handler().method(MovieController.class.getMethod("getAllMovies")))
                .andReturn().getResponse().getContentAsByteArray();

        Movie[] movies = TestUtil.convertJsonBytesToObject(jsonResponseBytes, Movie[].class);

        assertThat(movies.length).isEqualTo(2);
        assertThat(movies[0].getName()).isEqualTo("The Shawshank Redemption");
        assertThat(movies[0].getRating()).isEqualTo(9.3);
        assertThat(movies[1].getName()).isEqualTo("The Pursuit of Happyness");
        assertThat(movies[1].getRating()).isEqualTo(8.0);
    }

    @Test
    @Order(3)
    public void testGetMoviesByMinimumRatingSuccess() throws Exception {

        mockMvc.perform(get("/rating/8.0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(MovieController.class))
                .andExpect(handler().method(
                        MovieController.class.getMethod("getMoviesByMinimumRating", Double.class)))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].rating").value(9.3))
                .andExpect(jsonPath("$[1].name").value("The Pursuit of Happyness"))
                .andExpect(jsonPath("$[1].rating").value(8.0));

        mockMvc.perform(get("/rating/8.5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(MovieController.class))
                .andExpect(handler().method(
                        MovieController.class.getMethod("getMoviesByMinimumRating", Double.class)))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].rating").value(9.3));
    }

    @Test
    @Order(4)
    public void testGetMoviesByMinimumRatingFailure() throws Exception {
        mockMvc.perform(get("/rating/string-value"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(MovieController.class))
                .andExpect(handler().method(
                        MovieController.class.getMethod("getMoviesByMinimumRating", Double.class)));
    }

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Order(5)
    public void testGetAllMoviesFailure() throws Exception {

        movieRepository.deleteAll();

        mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
