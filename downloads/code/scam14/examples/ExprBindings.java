package nuthatch.examples;

import static nuthatch.examples.exprlang.ExprPatterns.*;
import nuthatch.examples.exprlang.Expr;
import nuthatch.examples.exprlang.ExprActionFactory;
import nuthatch.examples.exprlang.ExprCursor;
import nuthatch.examples.exprlang.ExprWalker;
import nuthatch.examples.exprlang.Type;
import nuthatch.library.Action;
import nuthatch.library.BaseMatchAction;
import nuthatch.library.MatchBuilder;
import nuthatch.pattern.Environment;

public class ExprBindings {
	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		ExprActionFactory af = ExprActionFactory.getInstance();

		MatchBuilder<Expr, Type, ExprCursor, ExprWalker> mb = af.matchBuilder();

		mb.add(and(var("x", type(Type.VAR)), ancestor(Let(var("x"), var("e"), _))), new BaseMatchAction<Expr, Type, ExprCursor, ExprWalker>() {
			@Override
			public int step(ExprWalker walker, Environment<ExprCursor> env) {
				System.out.println("var " + env.get("x").treeToString() + " = " + ExprToString.exprToString(env.get("e").getData()));
				return PROCEED;
			}
		});

		// if we come down into a Let, go to the second child
		mb.add(Let(_, _, _), af.action(af.down(af.go(2))));

		Action<ExprWalker> showBindings = mb.all();

		for(Expr e : new Expr[] { ExampleExpr.expr1, ExampleExpr.expr2, ExampleExpr.expr3, ExampleExpr.expr4, ExampleExpr.expr5 }) {
			System.out.println(ExprToString.exprToString(e) + ":");
			ExprWalker toTermWalker = new ExprWalker(e, af.walk(showBindings));
			// run it
			toTermWalker.start();
			// print the contents of S
		}
	}
}
