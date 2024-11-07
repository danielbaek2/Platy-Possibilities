export interface Need {
  id: number;
  title: string;
  description: string | null;
  quantity: number;
  cost: number;
  quantityFunded: number;
}
