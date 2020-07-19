import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/file/';

class FileService {
    upload(file) {
        let header = {
            Authorization: authHeader().Authorization,
            "Content-Type": "multipart/form-data"
        }
        return axios.post(API_URL + 'uploadContractFile', file, {headers: header});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()});
    }

    download(fileId) {
        return axios.get(API_URL + `download/${fileId}`, {headers: authHeader(), responseType: 'blob'});
    }

}

export default new FileService();