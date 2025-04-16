export interface OrderItem {
    productName: string;
    quantity: number;
    price: number;
}

export interface Order {
    id: number;
    username: string;
    createdAt: string;
    items: OrderItem[];
}
