module EvaluatorSpec (main, spec) where

import Test.Hspec
import Evaluator
import Data.List (nub)

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
    describe "duels" $ do
        it "should be 90 (6*5*1*3) different combinaisons" $ do
            (length . nub) duels `shouldBe` 90
    describe "aggregate" $ do
        it "should be 45 (6*5*1*3/2) different combinaisons" $ do
            (length . aggregate) duels `shouldBe` 45
