import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class ServerState {
    //accumulated transmission time of each server
    private int[] servTime;

    //corresponding server of each request->request assignment
    private int[] reqAssig;

    //total accumulated time
    private int total_time;

    static Servers serversList;
    static Requests requestsList;

    public ServerState(int users, int requests, int seedReq, int nserv, int nrep, int seedServ) throws Servers.WrongParametersException {
        this.servTime = null;
        this.reqAssig = null;
        this.total_time = 0;
        serversList = new Servers(nserv, nrep, seedServ);
        requestsList = new Requests(users, requests, seedReq);
    }

    public ServerState(ServerState state)
    {
        this.servTime = state.servTime.clone();
        this.reqAssig = state.reqAssig.clone();
        this.total_time = state.total_time;
    }

    // Useful functions
    public int get_total_time()
    {
        return total_time;
    }

    public int get_server(int id_request) { return reqAssig[id_request];}

    public double get_variance()
    {
        // Compute mean (average
        // of elements)
        double mean = total_time / serversList.size();

        // Compute sum squared
        // differences with mean.
        double sqDiff = 0;
        for (int w : servTime)
        {
            if(w != 0)
            {
                sqDiff += (w - mean) * (w - mean);
            }
        }
        return (double)sqDiff / serversList.size();
    }

    public int getMaxServerTime()
    {
        int max_value = Integer.MIN_VALUE;
        for(int w : servTime)
        {
            max_value = Integer.max(max_value, w);
        }
        return max_value;
    }

    public boolean isGoalState()
    {
        return false;
    }

    //state operators


    //change the server assigned to the request with id = "id"
    public void modify_server(int id, int serv)
    {
        int[] req = requestsList.getRequest(id);
        //the old transmission time due to the old server
        int oldTransmissionTime = serversList.tranmissionTime(reqAssig[id], req[0]);

        //the new transmission time due to the new server
        int newTransmissionTime = serversList.tranmissionTime(serv, req[0]);

        //update the accumulated transmission time of both the old and new server

        //old server
        servTime[reqAssig[id]] -= oldTransmissionTime;

        //new server
        servTime[serv] += newTransmissionTime;

        //update the server assigned
        reqAssig[id] = serv;

        //update the total time
        total_time = total_time - oldTransmissionTime + newTransmissionTime;
    }

    //check if the server assigned to the request id1 and id2 can be swapped
    public boolean can_swap_server(int id1, int id2)
    {
        if (id1 == id2) return false;

        int[] req1 = requestsList.getRequest(id1);

        int[] req2 = requestsList.getRequest(id2);

        Set<Integer> locationFile1 = serversList.fileLocations(req1[1]);

        Set<Integer> locationFile2 = serversList.fileLocations(req2[1]);

        return locationFile1.contains(reqAssig[id2]) && locationFile2.contains(reqAssig[id1]);
    }

    //swap the server assigned to the request id_1 i id_2
    public void swap_requests(int id_1, int id_2)
    {
        if(!can_swap_server(id_1, id_2)) return;
        int[] req1 = requestsList.getRequest(id_1);
        int[] req2 = requestsList.getRequest(id_2);

        //update the transmission time of each server
        int oldTransmissionTime1 = serversList.tranmissionTime(reqAssig[id_1], req1[0]);
        int oldTransmissionTime2 = serversList.tranmissionTime(reqAssig[id_2], req2[0]);

        int newTransmissionTime1 = serversList.tranmissionTime(reqAssig[id_1], req2[0]);
        int newTransmissionTime2 = serversList.tranmissionTime(reqAssig[id_2], req1[0]);

        servTime[reqAssig[id_1]] = servTime[reqAssig[id_1]] - oldTransmissionTime1 + newTransmissionTime1;
        servTime[reqAssig[id_2]] = servTime[reqAssig[id_2]] - oldTransmissionTime2 + newTransmissionTime2;

        //swap the server assigned to each request
        int aux = reqAssig[id_1];
        reqAssig[id_1] = reqAssig[id_2];
        reqAssig[id_2] = aux;

        //update the total time
        total_time = total_time - (oldTransmissionTime1+oldTransmissionTime2) + newTransmissionTime1 + newTransmissionTime2;
    }



    //Generates the two initial solution

    //assign to the first server that operates this request
    public void initial_sol_1()
    {
        //create the array which stores the working time information of each server
        int[] time = new int[serversList.size()];
        Arrays.fill(time, 0);

        //create the array which stores the server information of each server
        int[] reques = new int[requestsList.size()];

        int totalTime = 0;

        for (int i = 0; i < requestsList.size(); ++i) {

            //get the request
            int[] req = requestsList.getRequest(i);

            //get the first server which contains the requested file
            Set<Integer> location = serversList.fileLocations(req[1]);
            int targetServ = location.iterator().next();

            //assign the server to the request
            reques[i] = targetServ;

            //get the transmission time of this file
            int transmissionTime = serversList.tranmissionTime(targetServ, req[0]);

            //add the transmission time to the target server
            time[targetServ] += transmissionTime;

            //increasing the total time accumulated
            totalTime += transmissionTime;
        }
        servTime = time;
        reqAssig = reques;
        total_time = totalTime;
    }

    //assign to the server which has less transmission time of that request
    public void initial_sol_2()
    {
        //create the array which stores the working time information of each server
        int[] time = new int[serversList.size()];
        Arrays.fill(time, 0);

        //create the array which stores the server information of each server
        int[] reques = new int[requestsList.size()];

        int totalTime = 0;

        for (int i = 0; i < requestsList.size(); ++i) {

            //get the request
            int[] req = requestsList.getRequest(i);

            //find the server with less transmission time
            Set<Integer> location = serversList.fileLocations(req[1]);
            int minServer = location.iterator().next();
            int minTrans = serversList.tranmissionTime(minServer, req[0]);

            for (Integer serv : location) {
                int transmissionTime = serversList.tranmissionTime(serv, req[0]);
                if (minTrans > transmissionTime) {
                    minTrans = transmissionTime;
                    minServer = serv;
                }
            }

            //assign the server to the request
            reques[i] = minServer;

            //add the transmission time to the target server
            time[minServer] += minTrans;

            //increasing the total time accumulated
            totalTime += minTrans;
        }
        servTime = time;
        reqAssig = reques;
        total_time = totalTime;
    }

    //assign to a random first server that operates this request
    public void initial_sol_3()
    {
        //create the array which stores the working time information of each server
        int[] time = new int[serversList.size()];
        Arrays.fill(time, 0);

        //create the array which stores the server information of each server
        int[] reques = new int[requestsList.size()];

        int totalTime = 0;

        for (int i = 0; i < requestsList.size(); ++i) {

            //get the request
            int[] req = requestsList.getRequest(i);

            //get the random server which contains the requested file
            Set<Integer> location = serversList.fileLocations(req[1]);
            Random myRandom = new Random();
            //get the index of the random choosed server
            int chosed = myRandom.nextInt(location.size());
            int ite = 0;
            int targetServ = 0;
            for (int serv : location) {
                if (ite == chosed) {
                    targetServ = serv;
                    break;
                }
                ++ite;
            }

            //assign the server to the request
            reques[i] = targetServ;

            //get the transmission time of this file
            int transmissionTime = serversList.tranmissionTime(targetServ, req[0]);

            //add the transmission time to the target server
            time[targetServ] += transmissionTime;

            //increasing the total time accumulated
            totalTime += transmissionTime;
        }
        servTime = time;
        reqAssig = reques;
        total_time = totalTime;
    }
}
