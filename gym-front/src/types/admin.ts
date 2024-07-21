//看看需不需要改名为admin.ts

export interface LoginData{
    username: string;
    password: string;
}

export interface RegisterData{
    username: string;
    password: string;
    rePassword: string;
    phone: string;
}


// export type LoginResponse = {}