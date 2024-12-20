package utils;

import api.pojo.requests.CreateUpdateUserBuilder;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class DataGeneration {

    private static final Faker faker = new Faker();

    @Step("Generation data for new user with set id: '{id}'")
    public static CreateUpdateUserBuilder generateRandomUser(int id) {
        return CreateUpdateUserBuilder.builder()
                .id(id)
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().phoneNumber())
                .userStatus(faker.number().numberBetween(1, 5))
                .build();
    }
}