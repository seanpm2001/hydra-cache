/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hydracache.client.partition;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.hydracache.client.HydraCacheAdminClient;
import org.hydracache.server.Identity;

/**
 * Periodically polls the server for updates to the server partition nodes.
 * 
 * @author Tan Quach (tquach@jointsource.com)
 * @since 1.0
 */
public class PartitionUpdatesPoller extends Thread {

    private HydraCacheAdminClient adminClient;
    private Observable obs;

    /**
     * Provide a reference to an admin client and at least one observer.
     * 
     * @param adminClient The admin client to perform the refresh
     * @param listener The observer required to be notified upon update
     * @param listeners Any other observers interested 
     */
    public PartitionUpdatesPoller(HydraCacheAdminClient adminClient, Observer listener, Observer... listeners) {
        this.adminClient = adminClient;
        
        this.obs = new Observable();
        this.obs.addObserver(listener);
        
        if (listeners != null)
            for (Observer observer : listeners) {
                this.obs.addObserver(observer);
            }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        List<Identity> list = adminClient.listNodes();
        obs.notifyObservers(list);
    }

}
