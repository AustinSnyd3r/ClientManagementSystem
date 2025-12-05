package com.cm.clientservice;
import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.contract.CoachClientAgreementRequestDto;
import com.cm.clientservice.dto.contract.ContractAgreementStatusRequestDto;
import com.cm.clientservice.dto.contract.template.AgreementTemplateRequestDto;
import com.cm.clientservice.dto.contract.template.AgreementTemplateReuseRequestDto;
import com.cm.clientservice.dto.scheduling.exercise.ExerciseRequestDto;
import com.cm.clientservice.dto.scheduling.movement.MovementCreateRequestDto;
import com.cm.clientservice.dto.scheduling.movement.MovementResponseDto;
import com.cm.clientservice.dto.scheduling.movement.MovementReuseRequestDto;
import com.cm.clientservice.dto.scheduling.workout.WorkoutRequestDto;
import com.cm.clientservice.model.User;
import com.cm.clientservice.repository.UserRepository;
import com.cm.clientservice.service.AgreementTemplateService;
import com.cm.clientservice.service.CoachClientAgreementService;
import com.cm.clientservice.service.MovementService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.List;
import java.util.UUID;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import com.cm.clientservice.service.UserService;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CoachingControllerTests {

    @LocalServerPort
    private Integer port;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoachClientAgreementService coachClientAgreementService;

    @Autowired
    private AgreementTemplateService agreementTemplateService;

    @Autowired
    MovementService movementService;

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("testdb")
                    .withUsername("user")
                    .withPassword("pass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "http://localhost:" + port + "/clients/coaching";
        userRepository.deleteAll();
    }

    UUID createCoachUserInDbReturnAuthId(){
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setAddress("225 TestCoach Lane, Testville, 45135, Testax, United States of Testing.");
        userRequest.setEmail("test_coach@test.com");
        userRequest.setDateOfBirth("1970-01-01");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Coach");
        userRequest.setRegisteredDate("2025-10-10");

        UUID mockAuthId = UUID.randomUUID();

        userService.createUser(userRequest, String.valueOf(mockAuthId));
        return mockAuthId;
    }

    UUID createClientUserInDBReturnAuthId(){
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setAddress("45 TestClient Lane, Testville, 45135, Testax, United States of Testing.");
        userRequest.setEmail("test_client@test.com");
        userRequest.setDateOfBirth("1970-01-01");
        userRequest.setFirstName("Test");
        userRequest.setLastName("Client");
        userRequest.setRegisteredDate("2025-09-09");

        UUID mockAuthId = UUID.randomUUID();

        userService.createUser(userRequest, String.valueOf(mockAuthId));
        return mockAuthId;
    }

    UUID createMockMovementReturnId(){
        // make movement
        MovementCreateRequestDto dto = new MovementCreateRequestDto();
        dto.setDescription("This is a fake movement.");
        dto.setName("Test Movement");

        // return id of created.
        MovementResponseDto responseDto = movementService.createMovement(dto);
        return UUID.fromString(responseDto.getId());
    }

    ExerciseRequestDto createExerciseRequestDto(UUID movementId){
        // movement
        MovementReuseRequestDto movementReuse = new MovementReuseRequestDto();
        movementReuse.setId(String.valueOf(movementId));

        // exercise
        ExerciseRequestDto dto = new ExerciseRequestDto();
        dto.setMovement(movementReuse);
        dto.setCoachNotes("Mock Coach Notes");
        dto.setNumReps("1");
        dto.setNumSets("2");
        return dto;
    }

    UUID createWorkoutInUserScheduleAndReturnScheduleId(User client, List<ExerciseRequestDto> exerciseRequestDtos){
        WorkoutRequestDto dto = new WorkoutRequestDto();
        dto.setDate("2025-05-05");
        dto.setWorkoutNotes("Test workout notes");
        dto.setIsCompleted("TRUE");
        dto.setExercises(exerciseRequestDtos);
        dto.setTrainingScheduleId(String.valueOf(client.getTrainingSchedule().getId()));
        UUID scheduleId = UUID.fromString(userService.addWorkoutToSchedule(client.getAuthId(), dto).getId());
        return scheduleId;
    }

    UUID createCoachClientAgreementBetweenCoachAndClientReturnId(UUID coachAuth, UUID clientAuth) {
        User coach = userRepository.findByAuthId(coachAuth).get();
        User client = userRepository.findByAuthId(clientAuth).get();

        // template
        AgreementTemplateReuseRequestDto template = new AgreementTemplateReuseRequestDto();
        UUID templateId = createMockAgreementTemplateReturnId(coach);
        template.setId(templateId.toString());

        // request
        CoachClientAgreementRequestDto requestDto = new CoachClientAgreementRequestDto();
        requestDto.setStartDate("2025-05-05");
        requestDto.setTemplate(template);

        // agreement
        UUID agreementId = UUID.fromString(coachClientAgreementService.proposeCoachClientAgreement(coach, client, requestDto).getId());

        // client accept
        ContractAgreementStatusRequestDto status = new ContractAgreementStatusRequestDto();
        status.setUserIsInAgreement(Boolean.TRUE);
        userService.setAcceptanceStatusOfPendingAgreement(clientAuth, agreementId, status);

        return agreementId;
    }

    UUID createMockAgreementTemplateReturnId(User coach){
        AgreementTemplateRequestDto dto = new AgreementTemplateRequestDto();
        dto.setTemplateName("Mock Template");
        dto.setPaymentAmount("100.00");
        dto.setDaysBetweenPayments("10");
        dto.setAllowPublicTemplateReuse("TRUE");
        dto.setTermsAndConditions("Mock terms");

        return UUID.fromString(agreementTemplateService.createAgreementTemplate(coach, dto).getId());
    }



    //    getClients
    @Test
    public void getClientsWithNoClientsShouldReturnEmptyList() {
        UUID mockAuthId = createCoachUserInDbReturnAuthId();

        // Call controller endpoint
        Response response =
                RestAssured.given()
                        .header("X-AUTH-ID", mockAuthId.toString())
                        .accept(ContentType.JSON)
                        .when()
                        .get("/clients")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Verify returned JSON is an empty list.
        List<?> list = response.jsonPath().getList("$");
        assert list.isEmpty();
    }



    //    getClientSchedule
    @Test
    public void getClientScheduleWithValidClientAndOneWorkoutShouldReturnOneWorkout(){

        // Create client and coach
        UUID coachAuthId = createCoachUserInDbReturnAuthId();
        UUID clientAuthId = createClientUserInDBReturnAuthId();

        User client = userRepository.findByAuthId(clientAuthId).get();

        // Create a workout in the clients schedule
        UUID movementId = createMockMovementReturnId();
        ExerciseRequestDto exerciseRequestDto =  createExerciseRequestDto(movementId);
        createWorkoutInUserScheduleAndReturnScheduleId(client, List.of(exerciseRequestDto));

        // Create an agreement between client and coach.
        UUID agreementId = createCoachClientAgreementBetweenCoachAndClientReturnId(coachAuthId, clientAuthId);

        Response response =
                RestAssured.given()
                        .header("X-AUTH-ID", coachAuthId.toString())
                        .accept(ContentType.JSON)
                        .when()
                        .get("/clients/" + client.getId() + "/schedule")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Verify returned JSON is an empty list.
        List<?> workouts = response.jsonPath().getList("workouts");
        assert workouts.size() == 1;
    }


    //    deleteWorkoutFromClientSchedule
    @Test
    public void deleteWorkoutFromClientScheduleWithValidClientAndOneWorkout(){
        // Needs:
        // Coach user
        // Client user
        // training schedule, with 1 workout


        // Then -> Delete the workout by clientId and workoutId

        // Assert: getClientSchedule(clientId) has 0 workouts in it.
    }


    //    addWorkoutToClientSchedule
    @Test
    public void addWorkoutToClientScheduleValidClientShouldAddOneWorkout(){
        // Needs:

        // User coach
        // User client
        // training schedule with 0 workouts.

        // CoachClientAgreement between coach and client.
        // a valid AgreementTemplate

        // A workout
        // A movement for the workout to use.


        // Then -> add the workout to client schedule


        // Assert that the workout was added correctly. 1 workout and fields were right.
    }

//    proposeClientAgreement


//    withdrawCoachesAgreementAcceptance
//    createAgreementTemplate
//    getCoachesAgreementTemplates
}
