import React, { useEffect, useState } from "react";
import * as yup from "yup";
import {Formik, FormikValues} from "formik";
import { Button, Form } from "react-bootstrap";
import translate from "../../i18n/translate";
import {Configuration, SettingsControllerApi} from "../../footystats-frontendapi";

export const SettingsView = () => {
	const sca = new SettingsControllerApi(new Configuration({basePath: ""}));

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

	const [initialValues, setInitialValues] = useState({
		footyusername: "footy-username",
		footypassword: "footy-password",
	});

	const handleSubmit = (values: FormikValues) => {
		sca.saveSettings({
			settings: {
				footyStatsUsername: values.footyusername,
				footyStatsPassword: {
					password: values.footypassword,
				},
			},
		});
	};

	useEffect(() => {
		sca.loadSettings().then((response) =>
			setInitialValues({
				...initialValues,
				footyusername: response.footyStatsUsername,
				footypassword: response.footyStatsPassword?.password,
			}),
		);
	}, []);

	return (
		<>
			<Formik
				initialValues={initialValues}
				onSubmit={handleSubmit}
				validationSchema={validationSchema}
				enableReinitialize={true}
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
								{errors.footyusername as string}
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
								{errors.footypassword as string}
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
