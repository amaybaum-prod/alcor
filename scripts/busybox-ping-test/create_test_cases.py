#MIT License
#Copyright(c) 2020 Futurewei Cloud
#Permission is hereby granted, free of charge, to any person obtaining a copy 
#of this software and associated documentation files(the "Software"), to deal 
#in the Software without restriction, including without limitation the rights 
#to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
#copies of the Software, and to permit persons to whom the Software is 
#furnished to do so, subject to the following conditions:
#The above copyright notice and this permission notice shall be included in 
#all copies or substantial portions of the Software.
#THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
#IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
#FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
#AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
#LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
#OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

from create_test_setup import *

def create_security_groups(port):
    print("Creating security groups")
    security_groups ={}
    url = 'http://localhost:{}/project/{}/security-groups'.format(port, get_projectid())
    sg_info = read_config_file_section("security_groups")
    sginfo = json.loads(sg_info['security_group_info'])

    security_groups_info = read_config_file_section("test_case2")
    id_list = json.loads(security_groups_info['security_group_ids'])
    id_names = json.loads(security_groups_info['sg_names'])
    device_ids = json.loads(security_groups_info['device_ids'])
    for name, id in zip(id_names, id_list):
       sginfo['id'] = id
       sginfo['name'] = name
       security_groups["security_group"] = sginfo
       print("SG ", security_groups)
       post_httprequest(url, security_groups)
    return id_list,device_ids


def attach_subnets_to_router(rm_port, snm_port, router_id):
    url = 'http://localhost:{}/project/{}/routers/{}/add_router_interface'.format(rm_port, get_projectid(),router_id)
    print("attaching subnets to test cases")
    subnet_info = read_config_file_section("test_case1")
    id_list = json.loads(subnet_info['subnet_ids'])
    id_names = json.loads(subnet_info['subnet_names'])
    device_ids = json.loads(subnet_info['device_ids'])
    cidrs  = json.loads(subnet_info['cidrs'])
    ip_addrs  = json.loads(subnet_info['ip_addrs'])

    for id in id_list:
       subnet_info = {"subnet_id":id}
       put_httprequest(url, subnet_info)
    subnet = {}
    url = 'http://localhost:{}/project/{}/subnets'.format(snm_port, get_projectid())
    subnet_info = read_config_file_section("subnet_info")
    subnetinfo = json.loads(subnet_info['subnet_info'])
    for cidr, id, name in zip(cidrs, id_list, id_names):
       subnetinfo['cidr'] = cidr
       subnetinfo['id'] = id
       subnetinfo['name'] = name
       subnet["subnet"] = subnetinfo
       post_httprequest(url, subnet)
    return id_list,device_ids,ip_addrs


#test case 1 is with two nodes with different subnet ids in same same sg
def prepare_test_case_1(ip_mac, ser_port):
    print("Test case 1 setup starts...")
    serv = read_config_file_section("services")
    create_default_segment_table(ser_port["vpm"])
    change = {'change':'cidr','cidr':"10.0.0.0/16"}
    create_vpc(ser_port["vpm"], change)
    get_vpcs(ser_port["vpm"])
    create_node(ser_port["nm"], ip_mac)
    router_id =create_router_interface(ser_port["rm"])
    id_list, device_ids, ip_addrs = attach_subnets_to_router(ser_port["rm"], ser_port["snm"], router_id)
    get_subnets(ser_port["snm"])
    change_ports = {"change":["subnet_id","device_id","ip_addrs"],"subnet_id":id_list,"device_id":device_ids,"ip_addrs":ip_addrs}
    create_security_group(ser_port["sgm"])
    create_port_goal_states(ser_port["pm"], change_ports)

    ip_mac_db = get_mac_from_db()
    print("Test case 1. IP/MAC in ignite DB: ", ip_mac_db)
    return ip_mac_db


# test case 2 is with two nodes in same subnet and different seurity groups
def prepare_test_case_2(ip_mac, ser_port):
    print("Test case 2 setup starts...")
    serv = read_config_file_section("services")
    create_default_segment_table(ser_port["vpm"])
    create_vpc(ser_port["vpm"])
    create_node(ser_port["nm"], ip_mac)
    create_subnet(ser_port["snm"])
    id_list,device_ids = create_security_groups(ser_port["sgm"])
    change_ports = {"change":["security_groups","device_id"], "security_groups":id_list,"device_ids":device_ids}
    create_port_goal_states(ser_port["pm"], change_ports)
    ip_mac_db = get_mac_from_db()
    print("Test case 2. IP/MAC in ignite DB: ", ip_mac_db)
    return ip_mac_db


def create_port_goal_states(port, change_ports):
    url= 'http://localhost:{}/project/{}/ports'.format(port,get_projectid())
    port_info  = read_config_file_section("port_info")
    port_dict  = port_info['port_info']
    port_dict  = json.loads(port_dict, strict=False)
    port_name  = port_dict['name']
    port_id    = port_dict['id']
    node_name  = port_dict['binding:host_id']
    subnet_ids = []
    security_groups = []
    changes = change_ports['change']
    device_ids = []
    if 'subnet_id' in changes:
      subnet_ids = change_ports['subnet_id']
    else:
      # Adding the same subnet ID twice because it is going to be same for two ports we are creating
      subnet_ids.append(port_dict['subnet_id'])
      subnet_ids.append(port_dict['subnet_id'])
    if 'device_id' in changes:
      print("DDD device_id",change_ports['device_ids'])
      device_ids = change_ports['device_ids']
    else:
      print("Adding same device id twice...")
      # Adding the same device ID twice because it is going to be same for two ports we are creating
      device_ids.append(port_dict['device_id'])
      device_ids.append(port_dict['device_id'])
    if 'security_groups' in changes:
      security_groups = change_ports['security_groups']
    else:
      # Adding the same security group ID twice because it is going to be same for two ports we are creating
      security_groups.append(port_dict['security_groups'])
      security_groups.append(port_dict['security_groups'])
    if 'ip_addrs' in changes:
      ip_addrs = change_ports['ip_addrs']
    else:
      ip_addrs  = port_dict['fixed_ips']
    for index in range(len(ip_addrs)):
      ports = {}
      port_info = {"admin_state_up":True,"allowed_address_pairs":[{"ip_address":"11.11.11.11","mac_address":"00-AA-BB-15-EB-3F"}],"binding:host_id":node_name[index],"binding:vif_details":{},"create_at":"string","description": "string","device_id":device_ids[index],"device_owner": "compute:nova","dns_assignment": {},"dns_domain": "string","dns_name": "string","extra_dhcp_opts": [{"ip_version": "string","opt_name":"string","opt_value": "string"}],"fast_path": True,"fixed_ips":[{"ip_address": ip_addrs[index],"subnet_id":subnet_ids[index]}],"id": port_id[index],"mac_learning_enabled": True,"name": port_name[index],"network_id": port_dict['network_id'],"network_ns": "string","port_security_enabled": True,"project_id":port_dict['project_id'],"qos_network_policy_id": "string","qos_policy_id": "string","revision_number": 0,"security_groups": [security_groups[index]],"tags": ["string"],"tenant_id":port_dict['tenant_id'],"update_at": "string","uplink_status_propagation": True,"veth_name":"string"}
      ports["port"] = port_info
      print(ports, url)
      print("creating goal state :")
      post_httprequest(url, ports)


