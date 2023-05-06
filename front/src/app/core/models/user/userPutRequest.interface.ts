export interface UserPutRequest {
  email: string;
  username: string;
  password: string;
  token?: string;
}
