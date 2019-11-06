package com.futurewei.alcor.controller.app.demo;

import com.futurewei.alcor.controller.model.HostInfo;
import com.futurewei.alcor.controller.model.PortState;
import com.futurewei.alcor.controller.model.SubnetState;
import com.futurewei.alcor.controller.model.VpcState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DemoConfig {
//    String HOST_ID_PREFIX = "hostid-";
    public static boolean IS_Demo = true;

    public static String HOST_ID_PREFIX = "es7-";
    public static long Tunnel_Id = 3000;
    public static int OVERFLOW_IP_CONVERSION = 256;

    // TODO: figure out to store integer value over 127 in a byte
    public static byte FIRST_IP_BLOCK = (byte)(172-OVERFLOW_IP_CONVERSION);

    public static int GRPC_SERVER_PORT = 50051;
    public static BufferedWriter TIME_STAMP_WRITER;
    public static long TOTAL_TIME = 0;
    public static int TOTAL_REQUEST = 0;

    public static String gRPCServerIp = "10.213.43.166";
    public static int[] gRPCServerPortForSubnet1 = {
            50001,
            50002,
            50003,
            50004,
            50009,
            50010
    };

    static {
        try {
            TIME_STAMP_WRITER = new BufferedWriter(
                    new FileWriter("c:/temp/samplefile.txt", true)  //Set true for append mode
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////
    // Virtual network resources
    /////////////////////////////////
    public static String VETH_NAME = "veth0";

    public static String projectId = "dbf72700-5106-4a7a-918f-a016853911f8";
    public static String vpcId = "99d9d709-8478-4b46-9f3f-2206b1023fd3";
    public static String subnet1Id = "d973934b-93e8-42fa-ac91-bf0cdb84fffc";
    public static String subnet2Id = "8cb94df3-05bd-45d1-95c0-1ad75f929810";
    public static String ep1Id = "89e72582-b4fc-4e4e-b46a-6eee650e03f5";
    public static String ep2Id = "34bf0cec-0969-4635-b9a9-dd32611f35a4";
    public static String ep3Id = "64353fd7-b60c-4108-93ff-ecaa6b63a6a3";
    public static String ep4Id = "cae2df90-4a50-437e-a3f2-e3b742c8fbf8";
    public static String ep5Id = "364d2bbd-2def-4c70-9965-9ffd2165f43a";
    public static String ep6Id = "c60fe503-88a2-4198-a3be-85c197acd9db";
    public static String ep7Id = "38e45f95-5ea7-4d0a-9027-886febc27bdc";
    public static String ep8Id = "b81abf49-87ab-4a58-b457-93dc5a0dabac";

    public static VpcState customerVpcState =
            new VpcState(projectId, vpcId,
                    "SuperVpc",
                    "10.0.0.0/16");

    public static SubnetState customerSubnetState1 = new SubnetState(projectId, vpcId, subnet1Id,
            "Subnet1",
            "10.0.0.0/24",
            "10.0.0.5");
    public static SubnetState customerSubnetState2 = new SubnetState(projectId, vpcId, subnet2Id,
            "Subnet2",
            "10.0.1.0/24",
            "10.0.1.5");

    public static PortState[] customerPortStateForSubnet1 = {
            new PortState(projectId, subnet1Id, ep1Id,
                    DemoConfig.EP1_ID,
                    "0e:73:ae:c8:87:00",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.0.1"}),
            new PortState(projectId, subnet1Id, ep2Id,
                    DemoConfig.EP2_ID,
                    "0e:73:ae:c8:87:01",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.0.2"}),
            new PortState(projectId, subnet1Id, ep3Id,
                    DemoConfig.EP3_ID,
                    "0e:73:ae:c8:87:02",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.0.3"}),
            new PortState(projectId, subnet1Id, ep4Id,
                    DemoConfig.EP4_ID,
                    "0e:73:ae:c8:87:03",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.0.4"})
    };

    public static PortState[] customerPortStateForSubnet2 = {
            new PortState(projectId, subnet2Id, ep5Id,
                    DemoConfig.EP5_ID,
                    "0e:73:ae:c8:87:04",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.1.1"}),
            new PortState(projectId, subnet2Id, ep6Id,
                    DemoConfig.EP6_ID,
                    "0e:73:ae:c8:87:05",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.1.2"}),
            new PortState(projectId, subnet2Id, ep7Id,
                    DemoConfig.EP7_ID,
                    "0e:73:ae:c8:87:06",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.1.3"}),
            new PortState(projectId, subnet2Id, ep8Id,
                    DemoConfig.EP8_ID,
                    "0e:73:ae:c8:87:07",
                    DemoConfig.VETH_NAME,
                    new String[]{"10.0.1.4"})
    };

    /////////////////////////////////
    // Physical network resources
    /////////////////////////////////
    public static String TRANSIT_SWTICH_1_HOST_ID = "switchhost_1";
    public static byte[] TRANSIT_SWITCH_1_IP = new byte[]{FIRST_IP_BLOCK,17,0,15};
    public static String TRANSIT_SWITCH_1_MAC = "02:42:ac:11:00:0f";

    public static String TRANSIT_SWTICH_2_HOST_ID = "switchhost_2";
    public static byte[] TRANSIT_SWITCH_2_IP = new byte[]{FIRST_IP_BLOCK,17,0,16};
    public static String TRANSIT_SWITCH_2_MAC = "02:42:ac:11:00:10";

    public static String TRANSIT_SWTICH_3_HOST_ID = "switchhost_3";
    public static byte[] TRANSIT_SWITCH_3_IP = new byte[]{FIRST_IP_BLOCK,17,0,17};
    public static String TRANSIT_SWITCH_3_MAC = "02:42:ac:11:00:11";

    public static String TRANSIT_SWTICH_4_HOST_ID = "switchhost_4";
    public static byte[] TRANSIT_SWITCH_4_IP = new byte[]{FIRST_IP_BLOCK,17,0,18};
    public static String TRANSIT_SWITCH_4_MAC = "02:42:ac:11:00:12";

    public static String TRANSIT_ROUTER_1_HOST_ID = "routerhost_1";
    public static byte[] TRANSIT_ROUTER_1_IP = new byte[]{FIRST_IP_BLOCK,17,0,19};
    public static String TRANSIT_ROUTER_1_MAC = "02:42:ac:11:00:13";

    public static String TRANSIT_ROUTER_2_HOST_ID = "routerhost_2";
    public static byte[] TRANSIT_ROUTER_2_IP = new byte[]{FIRST_IP_BLOCK,17,0,20};
    public static String TRANSIT_ROUTER_2_MAC = "02:42:ac:11:00:14";

    public static HostInfo[] transitRouterHosts = {
            new HostInfo("vpc1-transit-router1", "transit router1 host", DemoConfig.TRANSIT_ROUTER_1_IP, DemoConfig.TRANSIT_ROUTER_1_MAC),
            new HostInfo("vpc1-transit-router2", "transit router2 host", DemoConfig.TRANSIT_ROUTER_2_IP, DemoConfig.TRANSIT_ROUTER_2_MAC)
    };

    public static HostInfo[] transitSwitchHostsForSubnet1 = {
            new HostInfo("subnet1-transit-switch1","transit switch1 host for subnet1", DemoConfig.TRANSIT_SWITCH_1_IP, DemoConfig.TRANSIT_SWITCH_1_MAC, DemoConfig.gRPCServerPortForSubnet1[4]),
            new HostInfo("subnet1-transit-switch2","transit switch2 host for subnet1", DemoConfig.TRANSIT_SWITCH_2_IP, DemoConfig.TRANSIT_SWITCH_2_MAC, DemoConfig.gRPCServerPortForSubnet1[5])
    };

    public static HostInfo[] transitSwitchHostsForSubnet2 = {
            new HostInfo("subnet2-transit-switch1","transit switch1 host for subnet2", DemoConfig.TRANSIT_SWITCH_3_IP, DemoConfig.TRANSIT_SWITCH_3_MAC),
            new HostInfo("subnet2-transit-switch2","transit switch2 host for subnet2", DemoConfig.TRANSIT_SWITCH_4_IP, DemoConfig.TRANSIT_SWITCH_4_MAC)
    };

    public static String EP1_ID = "ephost_1";
    public static byte[] EP1_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,7};
    public static String EP1_HOST_MAC = "02:42:ac:11:00:07";

    public static String EP2_ID = "ephost_2";
    public static byte[] EP2_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,8};
    public static String EP2_HOST_MAC = "02:42:ac:11:00:08";

    public static String EP3_ID = "ephost_3";
    public static byte[] EP3_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,9};
    public static String EP3_HOST_MAC = "02:42:ac:11:00:09";

    public static String EP4_ID = "ephost_4";
    public static byte[] EP4_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,10};
    public static String EP4_HOST_MAC = "02:42:ac:11:00:0a";

    public static String EP5_ID = "ephost_5";
    public static byte[] EP5_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,11};
    public static String EP5_HOST_MAC = "02:42:ac:11:00:0b";

    public static String EP6_ID = "ephost_6";
    public static byte[] EP6_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,12};
    public static String EP6_HOST_MAC = "02:42:ac:11:00:0c";

    public static String EP7_ID = "ephost_7";
    public static byte[] EP7_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,13};
    public static String EP7_HOST_MAC = "02:42:ac:11:00:0d";

    public static String EP8_ID = "ephost_8";
    public static byte[] EP8_HOST_IP = new byte[]{FIRST_IP_BLOCK,17,0,14};
    public static String EP8_HOST_MAC = "02:42:ac:11:00:0e";

    public static HostInfo[] epHostForSubnet1 = {
            new HostInfo("subnet1-ep1", "ep1 host", DemoConfig.EP1_HOST_IP, DemoConfig.EP1_HOST_MAC, DemoConfig.gRPCServerPortForSubnet1[0]),
            new HostInfo("subnet1-ep2", "ep2 host", DemoConfig.EP2_HOST_IP, DemoConfig.EP2_HOST_MAC, DemoConfig.gRPCServerPortForSubnet1[1]),
            new HostInfo("subnet1-ep3", "ep3 host", DemoConfig.EP3_HOST_IP, DemoConfig.EP3_HOST_MAC, DemoConfig.gRPCServerPortForSubnet1[2]),
            new HostInfo("subnet1-ep4", "ep4 host", DemoConfig.EP4_HOST_IP, DemoConfig.EP4_HOST_MAC, DemoConfig.gRPCServerPortForSubnet1[3]),
    };

    public static HostInfo[] epHostForSubnet2 = {
            new HostInfo("subnet2-ep1", "ep5 host", DemoConfig.EP5_HOST_IP, DemoConfig.EP5_HOST_MAC),
            new HostInfo("subnet2-ep2", "ep6 host", DemoConfig.EP6_HOST_IP, DemoConfig.EP6_HOST_MAC),
            new HostInfo("subnet2-ep3", "ep7 host", DemoConfig.EP7_HOST_IP, DemoConfig.EP7_HOST_MAC),
            new HostInfo("subnet2-ep4", "ep8 host", DemoConfig.EP8_HOST_IP, DemoConfig.EP8_HOST_MAC),
    };
}