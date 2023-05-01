import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private tokenKey = 'mdd-access-token';

  set(key: string, value: string) {
    localStorage.setItem(key, value);
  }

  get(key: string) {
    return localStorage.getItem(key);
  }

  remove(key: string) {
    localStorage.removeItem(key);
  }

  setToken(value: string) {
    localStorage.setItem(this.tokenKey, value);
  }

  getToken() {
    const token = localStorage.getItem(this.tokenKey);
    return token ? token : '';
  }

  removeToken() {
    localStorage.removeItem(this.tokenKey);
  }
}
