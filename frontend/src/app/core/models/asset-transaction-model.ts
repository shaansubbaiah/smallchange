export interface AssetTransactionModel {
  index_code: string;
  index_type: 'STOCK' | 'BOND' | 'MUTUALFUND';
  quantity: number;
  transaction_type: 'buy' | 'sell';
  user_id: string;
}
