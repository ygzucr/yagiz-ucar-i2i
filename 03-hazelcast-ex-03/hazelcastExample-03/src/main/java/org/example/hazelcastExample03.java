package org.example;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class hazelcastExample03 {
    public static void main(String[] args) {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("<PRIVATE ID>");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<Integer, Person> map = client.getMap("personMap");

        for (int personNumber=0; personNumber<10; personNumber++){
           map.put(personNumber, new Person("Person-"+personNumber));
        }

        System.out.println("10 Person added.");

        for (int getPerson=0; getPerson < 10; getPerson++){
            System.out.println(map.get(getPerson));
        }

        System.out.println("Total number of person: "+ map.size());

        client.shutdown();

    }

}
