export interface AssetTransactionModel {
  index_code: string;
  index_type: 'stock' | 'bond' | 'mf';
  quantity: number;
  transaction_type: 'buy' | 'sell';
  user_id: string;
}
