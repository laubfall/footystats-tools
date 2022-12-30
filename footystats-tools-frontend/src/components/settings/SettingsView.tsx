import React from "react";
import * as yup from "yup";
import { Formik } from "formik";
import { Button, Form } from "react-bootstrap";
import translate from "../../i18n/translate";

export const SettingsView = () => {
	const validationSchema = yup.object({
		footyusername: yup
			.string()
			.required(
				translate("renderer.settings.input.footyusername.required"),
			),
		footypassword: yup
			.string()
			.required(
				translate("renderer.settings.input.footypassword.required"),
			),
	});

	const initialValues = {
		footyusername: "foobar@example.com",
		footypassword: "foobar",
	};

	const handleSubmit = (values) => {
		alert(JSON.stringify(values, null, 2));
	};

	return (
		<>
			<Formik
				initialValues={initialValues}
				onSubmit={handleSubmit}
				validationSchema={validationSchema}
			>
				{({ values, errors, handleChange, handleSubmit }) => (
					<Form onSubmit={handleSubmit} noValidate>
						<Form.Group className="mb-3" controlId="footyusername">
							<Form.Label>
								{translate(
									"renderer.settings.input.footyusername",
								)}
							</Form.Label>
							<Form.Control
								type="text"
								onChange={handleChange}
								value={values.footyusername}
								isInvalid={!!errors.footyusername}
							/>
							<Form.Control.Feedback type="invalid">
								{errors.footyusername}
							</Form.Control.Feedback>
						</Form.Group>

						<Form.Group className="mb-3" controlId="footypassword">
							<Form.Label>
								{translate(
									"renderer.settings.input.footypassword",
								)}
							</Form.Label>
							<Form.Control
								type="password"
								value={values.footypassword}
								onChange={handleChange}
								isInvalid={!!errors.footypassword}
							/>
							<Form.Control.Feedback type="invalid">
								{errors.footypassword}
							</Form.Control.Feedback>
						</Form.Group>

						<Button variant="primary" type="submit">
							Submit
						</Button>
					</Form>
				)}
			</Formik>
		</>
	);
};
