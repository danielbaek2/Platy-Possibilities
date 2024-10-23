import { Need } from "./need";
import { User } from "./user";

export interface Helper {
    fundingBasket: Need[];
    user: User
}