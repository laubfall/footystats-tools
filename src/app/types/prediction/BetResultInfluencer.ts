import { BetPredictionContext } from './BetPredictionContext';

export enum PrecheckResult {
  // The current BetResult Influencer misses some information inside the BetPredictionContext
  // in order to do a proper calculation.
  NOT_ENOUGH_INFORMATION,
  // The current BetResult Influencer has no algorithm for the chosen bet
  DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET,
  // Error while doing calculation
  EXCEPTION,
  // Everything is fine, we can calculate a prediction value
  OK,
}

export type BetInfluencerCalculation = {
  // Influence for the chosen bet. Higher value means more influence. Expected values 0-100.
  amount: number;
};

export type PreCheckReturn = PrecheckResult;
export interface BetResultInfluencer {
  preCheck(ctx: BetPredictionContext): PreCheckReturn;
  calculateInfluence(ctx: BetPredictionContext): BetInfluencerCalculation;
  influencerName(): string;
}

export default BetResultInfluencer;
