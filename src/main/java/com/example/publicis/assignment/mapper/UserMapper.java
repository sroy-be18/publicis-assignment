package com.example.publicis.assignment.mapper;

import com.example.publicis.assignment.dto.UserResponse;
import com.example.publicis.assignment.model.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user){
        if(user == null){
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setAge(user.getAge());
        userResponse.setGender(user.getGender());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        if (user.getBirthDate() != null) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4,10, SignStyle.NORMAL)
                    .appendLiteral('-')
                    .appendValue(ChronoField.MONTH_OF_YEAR,1,2, SignStyle.NEVER)
                    .appendLiteral('-')
                    .appendValue(ChronoField.DAY_OF_MONTH,1,2, SignStyle.NEVER)
                    .toFormatter();
            userResponse.setBirthDate(java.time.LocalDate.parse(user.getBirthDate(), formatter));
        }
        userResponse.setSsn(user.getSsn());
        return userResponse;
    }
}
