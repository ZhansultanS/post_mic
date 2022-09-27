import {User} from "./user";

export interface AuthInfo {
  authenticated: boolean
  user?: User
}
