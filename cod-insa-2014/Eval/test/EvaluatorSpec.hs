module EvaluatorSpec (main, spec) where

import Test.Hspec
import Evaluator
import Data.List (nub)

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
    describe "duels" $ do
        it "should be 30 (6*5) different combinaisons" $ do
            (length . nub) duels `shouldBe` 30
