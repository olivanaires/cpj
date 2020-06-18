import axios from 'axios';

const API_URL = 'https://viacep.com.br/ws/';

class ClientService {

    getAddressByCep(cep) {
        return axios.get(API_URL + cep + '/json/');
    }

}

export default new ClientService();