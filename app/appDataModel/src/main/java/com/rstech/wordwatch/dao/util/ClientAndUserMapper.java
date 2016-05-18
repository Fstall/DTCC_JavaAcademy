package com.rstech.wordwatch.dao.util;

import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser; 

public interface ClientAndUserMapper { 
        public static class ClientWithUser {
                public RSClient      client;
                public RSUser        user;
        }
} 
 
