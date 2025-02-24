package com.bencesoft.commons.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String email;
    private String password;
    private List<String> roles;
}
