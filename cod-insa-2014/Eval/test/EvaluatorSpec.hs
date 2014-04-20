module EvaluatorSpec (main, spec) where

import Test.Hspec
import Evaluator
import Data.List (nub)

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
    describe "duels" $ do
        it "should be 15 (6*5*1*3/3/2) different combinaisons" $ do
            length duels `shouldBe` 15
