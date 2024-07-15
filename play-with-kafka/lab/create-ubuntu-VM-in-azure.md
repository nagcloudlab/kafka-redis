create a new VM in Azure using the Azure CLI. Ubuntu 22.04 LTS is the OS of choice. The VM will be created in the resource group named "myResourceGroup" and the VM will be named "myVM". The VM will be created in the Central India region. The VM will be created with a username of "azureuser" and a password of "Password1234!".

Configuration: 16GB RAM, 4 vCPUs, 30GB disk space, and a public IP address.

## Create a new resource group in the Central India region:

```bash
az group create --name myResourceGroup --location centralindia
```

## Create a new VM in the resource group:

```bash
az vm create \
  --resource-group myResourceGroup \
  --name myVM \
  --image Ubuntu2204 \
  --admin-username me \
  --admin-password Password1234! \
  --location centralindia \
  --size Standard_DS3_v2
```

# Check the status of the VM:

```bash
az vm show --resource-group myResourceGroup --name myVM --show-details
```

# Connect to the VM:

```bash
chmod 400 ~/id_rsa.pem
ssh -i ~/id_rsa.pem me@4.247.148.242
```

# Delete the VM:

```bash
az vm delete --resource-group myResourceGroup --name myVM --yes
```

# Delete the resource group:

```bash
az group delete --name myResourceGroup --yes
```
