import { Category } from "./category.model";

export interface Product {
    id?: number;
    name: string;
    description: string;
    year: number;
    type: string;
    price: number;
    img: string;
    category?: Category;
}