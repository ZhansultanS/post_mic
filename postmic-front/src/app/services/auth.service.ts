import { Injectable } from '@angular/core';
import {AuthInfo} from "../models/auth-info";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _authInfo: AuthInfo

  constructor() {
    this._authInfo = {
      authenticated: true,
      user: {
        id: 5,
        fullName: 'Zhansultan Salman',
        username: 'zhanssj',
        email: 'zhans@gmail.com'
      }
    }
  }

  get authInfo() {
    return this._authInfo
  }
}
